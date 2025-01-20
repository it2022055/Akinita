window.addEventListener('DOMContentLoaded', (event) => {
    const priceSlider = document.getElementById('priceSlider');
    const priceDisplay = document.getElementById('priceDisplay');
    priceSlider.addEventListener('input', function() {
        priceDisplay.textContent = '€' + priceSlider.value;
    });

    const sizeSlider = document.getElementById('sizeSlider');
    const sizeDisplay = document.getElementById('sizeDisplay');
    sizeSlider.addEventListener('input', function() {
        sizeDisplay.textContent = sizeSlider.value + ' m²';
    });

    const showExtraFieldsCheckbox = document.getElementById('showExtraFields');
    const dynamicFieldsContainer = document.getElementById('dynamicFields');
    const propertyTypeSelect = document.getElementById('propertyType');
    const buildingFeesField = document.getElementById('buildingFeesField');
    const constructionDateField = document.getElementById('constructionDateField');

    // Initially hide dynamic fields, building fees, and construction date
    dynamicFieldsContainer.style.display = 'none';
    buildingFeesField.style.display = 'none';
    constructionDateField.style.display = 'none';

    // Show extra fields when checkbox is clicked
    showExtraFieldsCheckbox.addEventListener('change', function() {
        if (this.checked) {
            dynamicFieldsContainer.style.display = 'grid'; // Εμφανίζει τα πεδία ως grid
        } else {
            dynamicFieldsContainer.style.display = 'none';
        }
    });

    // Show building fees and construction date field based on property type
    propertyTypeSelect.addEventListener('change', function() {
        if (this.value === 'House' || this.value === 'CommercialProperty') {
            buildingFeesField.style.display = 'block'; // Εμφανίζει το πεδίο για τα Building Fees
            constructionDateField.style.display = 'block'; // Εμφανίζει το πεδίο για το Construction Date
        } else if (this.value === 'Parking') {
            buildingFeesField.style.display = 'none'; // Κρύβει το πεδίο για τα Building Fees
            constructionDateField.style.display = 'block'; // Εμφανίζει το πεδίο για το Construction Date
        } else {
            buildingFeesField.style.display = 'none'; // Κρύβει το πεδίο για τα Building Fees
            constructionDateField.style.display = 'none'; // Κρύβει το πεδίο για το Construction Date
        }
    });
});
