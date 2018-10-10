$(document).ready ( function(){
    doRequest("GET");
});

$('.button').click(function(){
    doRequest($(this).text());
});

function doRequest(methodType){
        var params, reqType = methodType;

        if (reqType == "POST") {
            params = {newState:  $('#newState').val()};
        } else if (reqType == "PUT" || reqType == "DELETE") {
            if (markedId.get() == -1) {
                alert("Сначала выберите элемент списка");
                return;
            }
            params = {id: markedId.get(), newState:  $('#newState').val()};

        } else if (reqType != "GET") {
            return;
        }
   		$.ajax({
     			url: "change",
				type: reqType,
				data: params,
     			success: xmlParser
   			});
   			$('#'+markedId.get()).removeClass("markedList");
   			markedId.set(-1);
}

	 function xmlParser(xml){
	        $('#content').empty();
	        $('#info').empty();
	        $(xml).find("state").each(function () {
	            var htmlClass = "list";
	            if ($(this).find("id").text() % 2 == 0)
                    htmlClass = "list even";
                else
                    htmlClass = "list";
	            $('#content').append("<div class='"+htmlClass+"' id =" + $(this).find("id").text() + ">" + $(this).find("name").text() + "</div>");
            });
            $('#info').append("View count: " + $(xml).find("count").text() + ", the last one: " + $(xml).find("lastView").text());
        };

	$("#content").on('click','.list', function(){
    		var currentId = $(this).attr("id");
    		if (markedId.get() == currentId){
    			markedId.set(-1);
    			$(this).removeClass("markedList");
    		}
    		else {
    			$('#'+markedId.get()).removeClass("markedList");
    			$(this).addClass("markedList");
    			markedId.set(currentId);
    		}
    	});

	  var markedId = (function (){
		 var value = -1;
		  return {
		 	get: function() {
				return value;
		 	},
			set: function(newValue) {
			  value = newValue;
		  	}
		  }
	 }());