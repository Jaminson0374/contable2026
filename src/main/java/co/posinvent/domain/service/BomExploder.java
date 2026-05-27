package co.posinvent.domain.service;

import co.posinvent.domain.model.ProductFormula;
import co.posinvent.domain.repository.ProductFormulaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class BomExploder {

    private static final int MAX_DEPTH = 5;

    private final ProductFormulaRepository formulaRepo;

    public BomExploder(ProductFormulaRepository formulaRepo) {
        this.formulaRepo = formulaRepo;
    }

    public record ExplodedComponent(UUID productId, BigDecimal totalQuantity, int depth) {}

    /**
     * Explodes a formula recursively, returning all leaf-level raw material needs.
     *
     * @param formulaProductId the formula product to explode
     * @param quantity the quantity of formula product to produce
     * @return map of rawMaterialProductId → totalQuantity needed
     */
    public List<ExplodedComponent> explode(UUID formulaProductId, BigDecimal quantity) {
        var result = new ArrayList<ExplodedComponent>();
        explodeRecursive(formulaProductId, quantity, 0, result);
        return result;
    }

    private void explodeRecursive(UUID formulaProductId, BigDecimal quantity, int depth,
                                   List<ExplodedComponent> result) {
        if (depth > MAX_DEPTH) {
            throw new IllegalStateException(
                    "Profundidad máxima de BOM excedida (" + MAX_DEPTH + " niveles) para producto " + formulaProductId);
        }

        var components = formulaRepo.findByParentProductId(formulaProductId).stream()
                .filter(ProductFormula::active)
                .toList();

        if (components.isEmpty()) {
            throw new IllegalArgumentException(
                    "El producto " + formulaProductId + " no tiene fórmula definida y no puede ser producido");
        }

        for (var comp : components) {
            BigDecimal neededQty = comp.quantity().multiply(quantity);
            boolean isFormula = !formulaRepo.findByParentProductId(comp.componentProductId()).isEmpty();

            if (isFormula) {
                explodeRecursive(comp.componentProductId(), neededQty, depth + 1, result);
            } else {
                result.add(new ExplodedComponent(comp.componentProductId(), neededQty, depth));
            }
        }
    }

    /**
     * Detecta si agregar newComponentId como componente de formulaId crearía un ciclo.
     * DFS desde newComponentId hacia arriba en el BOM.
     */
    public boolean wouldCreateCycle(UUID formulaId, UUID newComponentId) {
        var visited = new HashSet<UUID>();
        return dfsAncestors(newComponentId, formulaId, visited);
    }

    private boolean dfsAncestors(UUID current, UUID target, Set<UUID> visited) {
        if (current.equals(target)) return true;
        if (!visited.add(current)) return false;

        var parents = formulaRepo.findAllByComponentProductId(current);
        for (var parent : parents) {
            if (dfsAncestors(parent.parentProductId(), target, visited)) return true;
        }
        return false;
    }
}
