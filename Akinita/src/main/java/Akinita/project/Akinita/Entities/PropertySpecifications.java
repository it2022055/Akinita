package Akinita.project.Akinita.Entities;

import Akinita.project.Akinita.Entities.Properties.Property;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecifications {

    public static Specification<Property> hasLocation(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%" + location + "%");
    }

    public static Specification<Property> hasPropertyType(String propertyType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("propertyType"), propertyType);
    }

    public static Specification<Property> hasMinPrice(Double minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Property> hasMaxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Property> hasMinSize(Double minSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("size"), minSize);
    }

    public static Specification<Property> hasMaxSize(Double maxSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("size"), maxSize);
    }

    public static Specification<Property> hasBuildingFees(String buildingFees) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("buildingFees"), buildingFees);
    }

    public static Specification<Property> hasConstructionDate(String constructionDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("constructionDate"), constructionDate);
    }

}
