CREATE FUNCTION prevent_food_changes() RETURNS trigger AS $$
BEGIN
RAISE EXCEPTION 'food is read-only (imported from CSV)';
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_food_immutable
BEFORE UPDATE OR DELETE ON food
FOR EACH ROW EXECUTE FUNCTION prevent_food_changes();