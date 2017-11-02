

var a = function(arg1, arg2) {
   document.getElementById("text").innerHTML = arg1 + arg2;
};

var b = new Function("arg1", 'arg2', 'document.getElementById("text").innerHTML = arg1 + arg2');

var k = new Function('document.getElementById("text").innerHTML = 18');

 function sum() {
     //a(1, 2);
     //b(1, 2)
     k();
 }