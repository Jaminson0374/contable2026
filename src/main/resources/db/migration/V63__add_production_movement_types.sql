ALTER TABLE inventory_movements DROP CONSTRAINT IF EXISTS inventory_movements_movement_type_check;
ALTER TABLE inventory_movements ADD CONSTRAINT inventory_movements_movement_type_check CHECK (movement_type IN (
    'ENTRY','EXIT','ADJUSTMENT','TRANSFER_IN','TRANSFER_OUT','DISPOSAL','RETURN',
    'PRODUCTION_CONSUMPTION','PRODUCTION_OUTPUT','PRODUCTION_SHRINKAGE'
));
