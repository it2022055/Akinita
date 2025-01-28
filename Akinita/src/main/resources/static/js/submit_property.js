function toggleSharedExpenses() {
    const propertyType = document.querySelector('select[name="propertyType"]').value;
    const sharedExpensesField = document.getElementById('shared-expenses-field');
    const form = document.querySelector('.form'); // Το κύριο form

    if (propertyType === "Parking") {
        sharedExpensesField.style.display = "block";
        form.classList.remove('default-form'); // Αφαιρεί την αρχική εμφάνιση
        form.classList.add('parking-form'); // Προσθέτει την εμφάνιση για "Parking"
    } else {
        sharedExpensesField.style.display = "none"; // Απόκρυψη του shared-expenses-field
        form.classList.remove('parking-form'); // Αφαιρεί την εμφάνιση για "Parking"
        form.classList.add('default-form');  // Επαναφέρει την αρχική εμφάνιση
    }
}