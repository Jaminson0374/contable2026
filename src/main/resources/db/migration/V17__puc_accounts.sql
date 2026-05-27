-- ============================================================
-- V17: Plan Único de Cuentas (PUC) - Colombia
-- Basado en Decreto 2420/2015 (NIIF Grupo 2 PYMES)
-- Incluye cuentas relevantes para empresa de alimentos/cárnicos
-- ============================================================

CREATE TABLE puc_accounts (
    id                  UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    code                VARCHAR(20)  NOT NULL UNIQUE,
    name                VARCHAR(255) NOT NULL,
    level               SMALLINT     NOT NULL CHECK (level BETWEEN 1 AND 5),
    -- 1=Clase  2=Grupo  3=Cuenta  4=Subcuenta  5=Auxiliar
    parent_code         VARCHAR(20)  REFERENCES puc_accounts(code),
    account_class       SMALLINT     NOT NULL CHECK (account_class BETWEEN 1 AND 9),
    -- 1=Activo 2=Pasivo 3=Patrimonio 4=Ingreso 5=Gasto 6=CostoVenta 7=CostoProd
    account_nature      VARCHAR(7)   NOT NULL CHECK (account_nature IN ('DEBITO','CREDITO')),
    -- DEBITO: aumenta al deber (activos, costos, gastos)
    -- CREDITO: aumenta al haber (pasivos, patrimonio, ingresos)
    allows_transactions BOOLEAN      NOT NULL DEFAULT false,
    -- Solo cuentas de nivel 4+ reciben movimientos directos (norma colombiana)
    active              BOOLEAN      NOT NULL DEFAULT true,
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_puc_parent       ON puc_accounts(parent_code);
CREATE INDEX idx_puc_class        ON puc_accounts(account_class);
CREATE INDEX idx_puc_level        ON puc_accounts(level);
CREATE INDEX idx_puc_code_prefix  ON puc_accounts(code varchar_pattern_ops);

-- ============================================================
-- SEED: Cuentas del PUC relevantes para el módulo
-- ============================================================

INSERT INTO puc_accounts (code,name,level,parent_code,account_class,account_nature,allows_transactions) VALUES

-- ──────────────────────────────────────────────────────────
-- CLASE 1: ACTIVO
-- ──────────────────────────────────────────────────────────
('1',      'ACTIVO',                                            1, NULL,   1,'DEBITO', false),

-- Grupo 11: Efectivo
('11',     'Efectivo y equivalentes de efectivo',              2,'1',    1,'DEBITO', false),
('1105',   'Caja',                                             3,'11',   1,'DEBITO', false),
('110505', 'Caja general',                                     4,'1105', 1,'DEBITO', true),
('110510', 'Caja menor',                                       4,'1105', 1,'DEBITO', true),
('1110',   'Depósitos en instituciones financieras',           3,'11',   1,'DEBITO', false),
('111005', 'Cuentas corrientes bancarias',                     4,'1110', 1,'DEBITO', true),
('111010', 'Cuentas de ahorros',                               4,'1110', 1,'DEBITO', true),

-- Grupo 13: Deudores
('13',     'Deudores',                                         2,'1',    1,'DEBITO', false),
('1305',   'Clientes',                                         3,'13',   1,'DEBITO', false),
('130505', 'Clientes nacionales - contado',                    4,'1305', 1,'DEBITO', true),
('130510', 'Clientes nacionales - crédito',                    4,'1305', 1,'DEBITO', true),

-- Grupo 14: Inventarios
('14',     'Inventarios',                                      2,'1',    1,'DEBITO', false),
('1405',   'Materias primas e insumos',                        3,'14',   1,'DEBITO', false),
('140505', 'Materias primas cárnicas',                         4,'1405', 1,'DEBITO', true),
('140510', 'Insumos de empaque y producción',                  4,'1405', 1,'DEBITO', true),
('1410',   'Productos en proceso',                             3,'14',   1,'DEBITO', false),
('141005', 'Productos en proceso - desposte',                  4,'1410', 1,'DEBITO', true),
('141010', 'Productos en proceso - embutidos',                 4,'1410', 1,'DEBITO', true),
('1430',   'Productos terminados',                             3,'14',   1,'DEBITO', false),
('143005', 'Cortes de carne fresca',                           4,'1430', 1,'DEBITO', true),
('143010', 'Embutidos y charcutería',                          4,'1430', 1,'DEBITO', true),
('143015', 'Productos adobados y apanados',                    4,'1430', 1,'DEBITO', true),
('143020', 'Chicharrón y derivados fritos',                    4,'1430', 1,'DEBITO', true),
('143025', 'Vísceras y subproductos',                          4,'1430', 1,'DEBITO', true),
('1435',   'Mercancías no fabricadas por la empresa',          3,'14',   1,'DEBITO', false),
('143505', 'Mercancías para la venta',                         4,'1435', 1,'DEBITO', true),
('1445',   'Semovientes',                                      3,'14',   1,'DEBITO', false),
('144505', 'Cerdos en pie',                                    4,'1445', 1,'DEBITO', true),
('1455',   'Materiales, repuestos y accesorios',               3,'14',   1,'DEBITO', false),
('145505', 'Materiales de empaque al vacío',                   4,'1455', 1,'DEBITO', true),
('145510', 'Bolsas y materiales de empaque',                   4,'1455', 1,'DEBITO', true),
('1499',   'Provisión para protección de inventarios',         3,'14',   1,'CREDITO',false),
('149905', 'Provisión inventarios por obsolescencia',          4,'1499', 1,'CREDITO',true),

-- Grupo 15: Propiedad planta y equipo
('15',     'Propiedades, planta y equipo',                     2,'1',    1,'DEBITO', false),
('1524',   'Equipo de oficina',                                3,'15',   1,'DEBITO', false),
('152405', 'Equipos de cómputo y comunicación',                4,'1524', 1,'DEBITO', true),
('1528',   'Equipo de transporte',                             3,'15',   1,'DEBITO', false),
('152805', 'Vehículos de reparto',                             4,'1528', 1,'DEBITO', true),
('1592',   'Depreciación acumulada',                           3,'15',   1,'CREDITO',false),
('159205', 'Depreciación acumulada - equipo de oficina',       4,'1592', 1,'CREDITO',true),
('159210', 'Depreciación acumulada - equipo de transporte',    4,'1592', 1,'CREDITO',true),

-- ──────────────────────────────────────────────────────────
-- CLASE 2: PASIVO
-- ──────────────────────────────────────────────────────────
('2',      'PASIVO',                                           1, NULL,   2,'CREDITO',false),
('22',     'Proveedores',                                      2,'2',    2,'CREDITO',false),
('2205',   'Proveedores nacionales',                           3,'22',   2,'CREDITO',false),
('220505', 'Proveedores de materias primas',                   4,'2205', 2,'CREDITO',true),
('220510', 'Proveedores de servicios',                         4,'2205', 2,'CREDITO',true),
('23',     'Cuentas por pagar',                                2,'2',    2,'CREDITO',false),
('2365',   'Retención en la fuente',                           3,'23',   2,'CREDITO',false),
('236505', 'Retención en la fuente - renta',                   4,'2365', 2,'CREDITO',true),
('236515', 'Retención en la fuente - ICA',                     4,'2365', 2,'CREDITO',true),
('2367',   'Impuesto a las ventas retenido',                   3,'23',   2,'CREDITO',false),
('236705', 'IVA retenido a proveedores',                       4,'2367', 2,'CREDITO',true),
('2408',   'Impuesto sobre las ventas por pagar (IVA)',        3,'23',   2,'CREDITO',false),
('240805', 'IVA generado tarifa 5%',                           4,'2408', 2,'CREDITO',true),
('240810', 'IVA generado tarifa 8%',                           4,'2408', 2,'CREDITO',true),
('240815', 'IVA generado tarifa 19%',                          4,'2408', 2,'CREDITO',true),
('240820', 'IVA descontable',                                  4,'2408', 2,'DEBITO', true),

-- ──────────────────────────────────────────────────────────
-- CLASE 3: PATRIMONIO
-- ──────────────────────────────────────────────────────────
('3',      'PATRIMONIO',                                       1, NULL,   3,'CREDITO',false),
('31',     'Capital social',                                   2,'3',    3,'CREDITO',false),
('3105',   'Capital suscrito y pagado',                        3,'31',   3,'CREDITO',false),
('310505', 'Capital social',                                   4,'3105', 3,'CREDITO',true),
('36',     'Resultados del ejercicio',                         2,'3',    3,'CREDITO',false),
('3605',   'Utilidad del ejercicio',                           3,'36',   3,'CREDITO',false),
('360505', 'Utilidad neta del período',                        4,'3605', 3,'CREDITO',true),
('3610',   'Pérdida del ejercicio',                            3,'36',   3,'DEBITO', false),
('361005', 'Pérdida neta del período',                         4,'3610', 3,'DEBITO', true),

-- ──────────────────────────────────────────────────────────
-- CLASE 4: INGRESOS
-- ──────────────────────────────────────────────────────────
('4',      'INGRESOS',                                         1, NULL,   4,'CREDITO',false),
('41',     'Ingresos operacionales',                           2,'4',    4,'CREDITO',false),
('4135',   'Comercio al por menor',                            3,'41',   4,'CREDITO',false),
('413505', 'Venta de cortes frescos y cárnicos',               4,'4135', 4,'CREDITO',true),
('413510', 'Venta de embutidos y charcutería',                 4,'4135', 4,'CREDITO',true),
('413515', 'Venta de productos adobados y apanados',           4,'4135', 4,'CREDITO',true),
('413520', 'Venta de chicharrón y fritos',                     4,'4135', 4,'CREDITO',true),
('413525', 'Venta de subproductos y vísceras',                 4,'4135', 4,'CREDITO',true),
('413530', 'Venta de insumos y accesorios',                    4,'4135', 4,'CREDITO',true),
('4155',   'Comercio al por mayor',                            3,'41',   4,'CREDITO',false),
('415505', 'Venta mayorista de cárnicos',                      4,'4155', 4,'CREDITO',true),
('415510', 'Venta mayorista de embutidos',                     4,'4155', 4,'CREDITO',true),
('42',     'Ingresos no operacionales',                        2,'4',    4,'CREDITO',false),
('4210',   'Descuentos en compras',                            3,'42',   4,'CREDITO',false),
('421005', 'Descuentos comerciales en compras',                4,'4210', 4,'CREDITO',true),
('4250',   'Recuperaciones',                                   3,'42',   4,'CREDITO',false),
('425005', 'Recuperación provisión inventarios',               4,'4250', 4,'CREDITO',true),

-- ──────────────────────────────────────────────────────────
-- CLASE 5: GASTOS
-- ──────────────────────────────────────────────────────────
('5',      'GASTOS',                                           1, NULL,   5,'DEBITO', false),
('51',     'Gastos operacionales de administración',           2,'5',    5,'DEBITO', false),
('5105',   'Personal',                                         3,'51',   5,'DEBITO', false),
('510505', 'Sueldos y salarios',                               4,'5105', 5,'DEBITO', true),
('510510', 'Prestaciones sociales',                            4,'5105', 5,'DEBITO', true),
('5120',   'Arrendamientos',                                   3,'51',   5,'DEBITO', false),
('512005', 'Arriendo locales y puntos de venta',               4,'5120', 5,'DEBITO', true),
('52',     'Gastos operacionales de ventas',                   2,'5',    5,'DEBITO', false),
('5205',   'Personal de ventas',                               3,'52',   5,'DEBITO', false),
('520505', 'Comisiones de venta',                              4,'5205', 5,'DEBITO', true),
('5245',   'Publicidad y propaganda',                          3,'52',   5,'DEBITO', false),
('524505', 'Publicidad y mercadeo',                            4,'5245', 5,'DEBITO', true),

-- ──────────────────────────────────────────────────────────
-- CLASE 6: COSTOS DE VENTAS
-- ──────────────────────────────────────────────────────────
('6',      'COSTOS DE VENTAS Y DE PRESTACIÓN DE SERVICIOS',   1, NULL,   6,'DEBITO', false),
('61',     'Costo de ventas y de prestación de servicios',     2,'6',    6,'DEBITO', false),
('6135',   'Comercio al por menor y al por mayor',             3,'61',   6,'DEBITO', false),
('613505', 'Costo de ventas - cortes frescos y cárnicos',      4,'6135', 6,'DEBITO', true),
('613510', 'Costo de ventas - embutidos y charcutería',        4,'6135', 6,'DEBITO', true),
('613515', 'Costo de ventas - productos adobados',             4,'6135', 6,'DEBITO', true),
('613520', 'Costo de ventas - chicharrón y fritos',            4,'6135', 6,'DEBITO', true),
('613525', 'Costo de ventas - subproductos y vísceras',        4,'6135', 6,'DEBITO', true),
('6165',   'Devoluciones en ventas',                           3,'61',   6,'CREDITO',false),
('616505', 'Devoluciones en ventas de cárnicos',               4,'6165', 6,'CREDITO',true),

-- ──────────────────────────────────────────────────────────
-- CLASE 7: COSTOS DE PRODUCCIÓN
-- ──────────────────────────────────────────────────────────
('7',      'COSTOS DE PRODUCCIÓN Y OPERACIÓN',                 1, NULL,   7,'DEBITO', false),
('71',     'Materia prima directa',                            2,'7',    7,'DEBITO', false),
('7105',   'Materias primas - cárnicos',                       3,'71',   7,'DEBITO', false),
('710505', 'Cerdos en canal',                                  4,'7105', 7,'DEBITO', true),
('710510', 'Semovientes - cerdos en pie',                      4,'7105', 7,'DEBITO', true),
('72',     'Mano de obra directa',                             2,'7',    7,'DEBITO', false),
('7205',   'Sueldos y jornales',                               3,'72',   7,'DEBITO', false),
('720505', 'Mano de obra despostadores y operarios',           4,'7205', 7,'DEBITO', true),
('73',     'Costos indirectos de fabricación',                 2,'7',    7,'DEBITO', false),
('7305',   'Materiales indirectos',                            3,'73',   7,'DEBITO', false),
('730505', 'Insumos de empaque y embalaje',                    4,'7305', 7,'DEBITO', true),
('730510', 'Materiales de limpieza y desinfección',            4,'7305', 7,'DEBITO', true),
('7310',   'Servicios públicos de producción',                 3,'73',   7,'DEBITO', false),
('731005', 'Energía eléctrica - cuartos fríos',                4,'7310', 7,'DEBITO', true),
('731010', 'Agua - producción',                                4,'7310', 7,'DEBITO', true);
