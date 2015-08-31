/**
 * Created by OPSKMC on 8/31/15.
 */

/**
 * Makes a csv string out of all the selected checkboxes with name=checkBoxName
 * @param checkBoxName name of the checkboxes
 * @returns {string}
 */
function checkBoxToCSV(checkBoxName) {
    var allOptions = document.getElementsByName(checkBoxName);
    var selectedOptions = ""
    for (var i = 0; i < allOptions.length; i++) {
        if (allOptions[i].checked) {
            selectedOptions += allOptions[i].value + ",";
        }

    }
    return selectedOptions;
}
/**
 * repeats the function removeCheckBoxesAndCreateSingleCsvField for an array of checkboxes
 * @param form reference of the form node
 * @param checkBoxNameArray
 */
function replaceCheckBoxesToCSV(form, checkBoxNameArray) {
//    alert(checkBoxNameArray[0]);
    for (var i = 0; i < checkBoxNameArray.length; i++) {
        alert('inside loop');
//        var csv = checkBoxToCSV(checkBoxNameArray[i]);
//        alert(csv);
        removeCheckBoxesAndCreateSingleCsvField(form, checkBoxNameArray[i]);
//        console.log(csv + '\n');
    }

}
/**
 * remove all check boxes in the form with checkBoxName. Create a new element in the form with name=csvName and value= csv string of selected checkbox values
 * @param form reference of the form node
 * @param checkBoxName name of the checkbox
 */
function removeCheckBoxesAndCreateSingleCsvField(form, checkBoxName) {
    var csv = checkBoxToCSV(checkBoxName);
    var allCheckBoxes = document.getElementsByName(checkBoxName);
    for (var i = 0; i < allCheckBoxes.length; i++) {

        allCheckBoxes[i].remove();
    }
    var element = document.createElement("input");
    element.setAttribute("name", checkBoxName);
    element.setAttribute("value", csv);
    element.setAttribute("type", "hidden");
    form.appendChild(element);

}