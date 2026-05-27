package co.posinvent.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee_details")
@Getter
@Setter
class EmployeeDetailsEntity {

    @Id
    private UUID id;

    @Column(length = 100)
    private String position;

    @Column(name = "cost_center", length = 50)
    private String costCenter;

    @Column(name = "work_center", length = 50)
    private String workCenter;

    @Column(length = 20)
    private String gender;

    @Column(name = "civil_status", length = 20)
    private String civilStatus;

    @Column(name = "sales_group", length = 50)
    private String salesGroup;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "birth_place", length = 100)
    private String birthPlace;

    @Column(name = "military_id", length = 50)
    private String militaryId;

    @Column(name = "is_foreigner", nullable = false)
    private boolean isForeigner;

    @Column(name = "nat_resident_exterior", nullable = false)
    private boolean natResidentExterior;

    @Column(name = "is_declarant", nullable = false)
    private boolean isDeclarant;

    @Column(name = "associated_seller", length = 100)
    private String associatedSeller;

    @Column(name = "requires_endowment", nullable = false)
    private boolean requiresEndowment;

    @Column(name = "is_senior", nullable = false)
    private boolean isSenior;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private ThirdPartyEntity thirdParty;
}
