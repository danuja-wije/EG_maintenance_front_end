//hide the alert boxes
$(document).ready(function () {
    console.log("ready");
    $("#alertSuccess").hide();
    $("#alertError").hide();
    //var contex = $("#contextPath").val();
    $(".gridView").html(
        $.ajax(
            {
                url: "MaintenanceServlet",
                type: "GET",
                complete: function (response, status) {
                    onLoadComplete(response.responseText, status);
                }
            }
        )
    );
});


$(document).on('click', "#btnSave", function (event) {
// Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
// Form validation-------------------
    var status = validateItemForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
// If valid------------------------
    var type = ($("#hiddenIDSAve").val() == "") ? "POST" : "PUT";
	console.log($("#formData").serialize());
    $.ajax({
        url: "MaintenanceServlet",
        type: type,
        data: $("#formData").serialize(),
        dataType: "text",
        complete: function (response, status) {
            onItemSaveComplete(response.responseText, status);
        }
    });
});
$(document).on("click", ".btnUpdate", function (event) {    
    $("#hiddenIDSAve").val($(this).data("intid"));
    $("#title").val($(this).closest("tr").find('td:eq(0)').text());
    $("#selectedInt").val($(this).closest("tr").find('td:eq(1)').text())
    $("#selectedInt").html($(this).closest("tr").find('td:eq(1)').text())
    $("#desc").val($(this).closest("tr").find('td:eq(2)').text());
    $("#sDate").val(tConvert($(this).closest("tr").find('td:eq(3)').text()));
    $("#eDate").val(tConvert($(this).closest("tr").find('td:eq(4)').text()));
     var custList = $(this).closest("tr").find('td:eq(5)').text()
    custList = custList.replace('[', '');
    custList = custList.replace(']', '');
    $("#custIDs").val(custList);
    $("#approval").val($(this).closest("tr").find('td:eq(6)').text());
    $("#handledby").val($(this).closest("tr").find('td:eq(7)').text());
});

$(document).on("click", ".btnRemove", function (event) {
    $.ajax(
        {
            url: "MaintenanceServlet",
            type: "DELETE",
            data: "intID=" + $(this).data("intid"),
            dataType: "text",
            complete: function (response, status) {
                onItemDeleteComplete(response.responseText, status);
            }
        });
});

function validateItemForm() {
    return true;
}

function onItemSaveComplete(response, status) {
    if (status == "success") {

        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() == "success") {

            $(".gridView").html(resultSet.data);
            $("#alertSuccess").text(status);
            $("#alertSuccess").show();
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while loading.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while loading..");
        $("#alertError").show();
    }
    $("#intType").val("");
    $("#hiddenIDSAve").val("");
    $("#formData")[0].reset();
}


function onLoadComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "success") {
            $(".gridView").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while loading.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while loading..");
        $("#alertError").show();
    }
}

function onItemDeleteComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "success") {
            $(".gridView").html(resultSet.data);
            $("#alertSuccess").text(status);
            $("#alertSuccess").show();
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while deleting.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while deleting..");
        $("#alertError").show();
    }
    $("#hiddenIDSAve").val("");
    
    $("#formData")[0].reset();
}
function tConvert (date) {
    // Check correct time format and split into components
    var dateAr = date.split(" ");
    var d = dateAr[0];
    var time = dateAr[1];
    var timeAr = time.split(":");
    var time = timeAr[0]+":"+timeAr[1];
    var convDate = d +"T"+time; 
    return convDate; // return adjusted time or original string
  }


