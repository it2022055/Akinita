function toggleSharedExpenses() {
    const propertyType = document.querySelector('select[name="propertyType"]').value;
    const sharedExpensesField = document.getElementById('shared-expenses-field');
    const form = document.querySelector('.form');

    if (propertyType === "Parking") {
        sharedExpensesField.style.display = "block";
        form.classList.remove('default-form');
        form.classList.add('parking-form');
    } else {
        sharedExpensesField.style.display = "none";
        form.classList.remove('parking-form');
        form.classList.add('default-form');
    }
}