package planet;

import interfaces.EntityWithCustomIdDAO;

import java.util.List;

public class PlanetCrudService {

    private final EntityWithCustomIdDAO<Planet> dao;

    public PlanetCrudService(EntityWithCustomIdDAO<Planet> dao){
        this.dao = dao;
    }

    public Planet create(Planet planet){
        if (planet == null) {
            throw new IllegalArgumentException("Planet mustn't be a null");
        }

        String name = planet.getName().trim();
        checkName(planet.getName());
        checkId(planet.getId());

        planet.setName(name);

        return dao.update(planet);
    }

    public Planet getById(String id){
        checkId(id);

        return dao.get(id);
    }

    public Planet update(Planet planet){
        if (planet == null) {
            throw new IllegalArgumentException("Planet mustn't be a null");
        }

        String name = planet.getName().trim();
        checkName(planet.getName());
        checkId(planet.getId());

        planet.setName(name);

        return dao.update(planet);
    }

    public Planet delete(String id){
        checkId(id);

        return dao.delete(id);
    }

    public List<Planet> getAll(){
        return dao.getAll();
    }

    private void checkName(String name) throws IllegalArgumentException{
        // Step 1: Check empty value
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must be not empty value!");
        }

        // Step 2: Check length
        int length = name.trim().length();
        if (length < 1 || length > 500) {
            throw new IllegalArgumentException("Name length must be between 2 and 1000!");
        }
    }

    private void checkId(String id) throws IllegalArgumentException{
        // Step 1: Check empty value
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id must be not empty value!");
        }

        // Step 2: Check length
        int length = id.trim().length();
        if (length < 1 || length > 100) {
            throw new IllegalArgumentException("Name length must be between 2 and 1000!");
        }

        // Step 3: Validate regexp
        if (!id.matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException("Id must contain exclusively Latin uppercase letters and digits!");
        }
    }

}
