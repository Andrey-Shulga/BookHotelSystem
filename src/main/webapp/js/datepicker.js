$(function () {
    var dateFormat = "dd/mm/yy",
        from = $("#from")
            .datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                dateFormat: dateFormat,
                minDate: new Date(),
            })
            .on("change", function () {
                var toMinDate = getDate(this);
                toMinDate.setDate(toMinDate.getDate() + 1);

                to.datepicker("option", "minDate", toMinDate);
            }),
        to = $("#to").datepicker({
            defaultDate: "+1w",
            changeMonth: true,
            dateFormat: dateFormat,
            minDate: new Date(),
        })
            .on("change", function () {
                var fromMaxDate = getDate(this);
                fromMaxDate.setDate(fromMaxDate.getDate() - 1);
                from.datepicker("option", "maxDate", fromMaxDate);
            });

    function getDate(element) {
        var date;
        try {
            date = $.datepicker.parseDate(dateFormat, element.value);
        } catch (error) {
            date = null;
        }

        return date;
    }
});
