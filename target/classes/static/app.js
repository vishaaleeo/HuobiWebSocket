var stompClient = null;

/*function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#userinfo").html("");
}
*/

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
  //  setConnected(false);
   // console.log("Disconnected");
}


function showDetails(message) {


var tableHeader="<table><tr><th>Price</th><th>Amount</th></tr>";
var tableFooter="</table>";
var tableContent="";
var jsonDetails =JSON.parse(message);

for(var i=0;i<jsonDetails.asks.length;i++) {
tableContent+="<tr><td>"+jsonDetails.asks[i][0]+"</td><td>"+jsonDetails.asks[i][1]+"</td></tr>";

//console.log(i+"     "+jsonDetails.asks[i][0]+"    "+jsonDetails.asks[i][1]);


}
document.getElementById("asks_table").innerHTML ="asks"+tableHeader + tableContent + tableFooter;

tableContent="";
for(var i=0;i<jsonDetails.bids.length;i++) {
tableContent+="<tr><td>"+jsonDetails.bids[i][0]+"</td><td>"+jsonDetails.bids[i][1]+"</td></tr>";

}
document.getElementById("bids_table").innerHTML ="bids"+tableHeader + tableContent + tableFooter;
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

     var socket = new SockJS('http://localhost:8081/huobi');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
         //   setConnected(true);
           // console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/marketDepth', function (message) {
            //console.log(message);
             showDetails(message.body);
            });
        });

    $( "#send" ).click(function() { sendName(); });
});