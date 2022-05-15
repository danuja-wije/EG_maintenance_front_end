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

//insert action
$(document).on('click', "#btnSave", function (event) {
// Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
// Form validation-------------------
    var status = validateItemForm();
    if (status == true) {
// If valid------------------------
    var type = ($("#hiddenIDSAve").val() == "") ? "POST" : "PUT";

    $.ajax({
    url: "MaintenanceServlet",
    type: type,
    data: $("#formData").serialize(),
    dataType: "text",
    complete: function (response, status) {
        onItemSaveComplete(response.responseText, status);
    }
});
    }

});

//update action
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

//delete action
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

//form validation
function validateItemForm() {
	if($("#intType").val()== ""){
		hideAllertValidate("#validateIntType");
		$("#validateIntType").html("**Interruption type required!")
		return false;
	}
    else if($("#title").val()== ""){
		hideAllertValidate("#validateTitle");
		$("#validateTitle").html("**Title required!")
		return false;
    }
    else if($("#desc").val()== ""){
	hideAllertValidate("#validateDesc");
        $("#validateDesc").html("**Description required!")
        return false;
    }
    else if($("#custIDs").val()== ""){
	hideAllertValidate("#validateCustIDs");
        $("#validateCustIDs").html("**Customer required!")
        return false;
    }
    else if($("#sDate").val()== ""){
	hideAllertValidate("#validateSDate");
        $("#validateSDate").html("**Start date required!")
        return false;
    }
    else if($("#eDate").val()== ""){
	hideAllertValidate("#validateEDate");
        $("#validateEDate").html("**End date required!")
        return false;
    }
    else if($("#handledby").val()== ""){
	hideAllertValidate("#validateHandledBy");
        $("#validateHandledBy").html("**Handled by required!")
        return false;
    }
    else{
        return true;
    }
  
}

//this function will display only the effected validate error and others will hide
function hideAllertValidate(target){
	$("#validateIntType").hide();
	$("#validateTitle").hide();
	$("#validateDesc").hide();
	$("#validateCustIDs").hide();
	$("#validateSDate").hide();
	$("#validateEDate").hide();
	$("#validateHandledBy").hide();
	
	$(target).show();
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
    $("#intType").html("Choose...");
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

  // correct time format and split into components
function tConvert (date) {
    var dateAr = date.split(" ");
    var d = dateAr[0];
    var time = dateAr[1];
    var timeAr = time.split(":");
    var time = timeAr[0]+":"+timeAr[1];
    var convDate = d +"T"+time; 
    return convDate; // return adjusted time 
  }


