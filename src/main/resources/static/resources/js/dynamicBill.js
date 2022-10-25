function parseDate(str) {//mdy->ymd
    var ymd = str.split('-');
    return new Date(ymd[0], ymd[1]-1, ymd[2]);
}
function dateDiff(first, second) {
    return Math.round((second-first)/(1000*60*60*24));
}

var Rupiah=Intl.NumberFormat("id-ID", {
      style: "currency",
      currency: "IDR"
    })

function calculateBill(){
    let checkIn=document.getElementById("checkIn").value;
    let checkOut=document.getElementById("checkOut").value;
    let price=document.getElementById("price").value;

    let totalDays= 0;
    let bill = 0;

    if((checkIn !== "")&&(checkOut !== "")){
         totalDays=datediff(parseDate(checkIn),parseDate(checkOut))+1;

         total=payPerDay*diff;

         }
    document.getElementById("bills").value=isNaN(total)?0:Math.max(total,0);
    document.getElementById("showBills").innerHTML=Rupiah.format(isNaN(total)?0:Math.max(total,0));
}