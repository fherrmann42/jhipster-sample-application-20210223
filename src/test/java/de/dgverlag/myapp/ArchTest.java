package de.dgverlag.myapp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("de.dgverlag.myapp");

        noClasses()
            .that()
            .resideInAnyPackage("de.dgverlag.myapp.service..")
            .or()
            .resideInAnyPackage("de.dgverlag.myapp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..de.dgverlag.myapp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
