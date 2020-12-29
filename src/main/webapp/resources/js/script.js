const canvas = $("#graphicCanvas")[0];
const rect = canvas.getBoundingClientRect();
const form = $("#form")[0];
const width = rect.right - rect.left;
const height = rect.bottom - rect.top;

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('graphicCanvas').addEventListener('click', (e) => {
        processCanvasClick(e);
    })
    //default value
    form.elements["form:r"].value = 3;
    drawBase();
})

function drawBase() {
    let ctx = canvas.getContext('2d');
    let width = canvas.width;
    let height = canvas.height;
    let centerX = width / 2;
    let centerY = height / 2;
    let r = height / 3;

    //CLEAR
    ctx.fillStyle = 'white';
    ctx.beginPath();
    ctx.rect(0,0, 800, 400);
    ctx.fill()

    ctx.fillStyle = 'orange';
    //TRIANGLE
    ctx.beginPath()
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX, centerY - r / 2);
    ctx.lineTo(centerX - r, centerY);
    ctx.lineTo(centerX, centerY);
    ctx.fill();

    //rect
    ctx.beginPath();
    ctx.fillRect(centerX, centerY, -r, r);

    //circle
    ctx.beginPath();
    ctx.arc(centerX, centerY, r/2, 0, Math.PI/180 * 90);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    //AXIS
    ctx.fillStyle = 'black';

    //X
    ctx.beginPath();
    ctx.moveTo(centerX - centerY * 1.5, centerY);
    ctx.lineTo(centerX + centerY * 1.5, centerY);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(centerX + centerY * 1.5, centerY);
    ctx.lineTo(centerX + centerY * 1.5 - 10, centerY - 10);
    ctx.lineTo(centerX + centerY * 1.5 - 10, centerY + 10);
    ctx.closePath();
    ctx.fill();

    //Y
    ctx.moveTo(centerX, height);
    ctx.lineTo(centerX, 0);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX - 10, 10);
    ctx.lineTo(centerX + 10, 10);
    ctx.closePath();
    ctx.fill();

    drawDots();
}

function drawDots() {
    let dots = getDots();
    dots.map(dot => drawDot(dot));
}

function getDots() {
    let dots = [];
    let tableRows = document.querySelectorAll("tbody tr")
    tableRows.forEach(tr => {
        let td = tr.children;
        let x = td[0].textContent;
        let y = td[1].textContent;
        let r = td[2].textContent;
        let result = td[3].textContent;
        if(td == "" || x == "" || y == "" || r == "") {
            console.log("Чел, что ты наделал?");
        } else {
            dots.push({x: x, y: y, r: r, result: result});
        }
    })
    return dots;
}


document.getElementById("form:submitButton").addEventListener("click", e => {
    let x = form.elements["form:x_hinput"].value;
    let y = form.elements["form:y_hinput"].value;
    let r = form.elements["form:r"].value;
    if(checkXY(x, y)) {
        drawDot(makeDot(x, y, r));
        recoverAlertLabels();
    }
})

form.elements["form:submitButton"].click(function () {
    let x = form.elements["form:x_hinput"].value;
    let y = form.elements["form:y_hinput"].value;
    let r = form.elements["form:r"].value;
    if(checkXY(x, y)) {
        drawDot(makeDot(x, y, r));
        recoverAlertLabels();
    }
})

function processCanvasClick(e) {
    let r =  form.elements["form:r"].value;
    let humanX = getHumanXFromPhysical(e.clientX);
    let humanY = getHumanYFromPhysical(e.clientY);
    if(checkXY(humanX, humanY)) {
        sendThisShit(humanX, humanY);
        drawDot(makeDot(humanX, humanY, r));
        recoverAlertLabels();
    } else {

    }
}

function getHumanXFromPhysical(clientX) {
    let r = form.elements["form:r"].value;
    return r * (clientX - rect.left - width / 2) / (height / 3);
}

function getHumanYFromPhysical(clientY) {
    let r = form.elements["form:r"].value;
    return -r * (clientY - rect.top - height / 2) / (height / 3);
}

function checkXY(actualX, actualY) {
    let isODZOk = true;

    if(actualX < -4 || actualX > 4 || actualX === "") {
        isODZOk = false;
        makeLabelAlert("X")
    }

    if(actualY < -3 || actualY > 5 || actualY === "") {
        isODZOk = false;
        makeLabelAlert("Y")
    }

    return isODZOk;
}

function drawDot(dot) {
    let ctx = canvas.getContext('2d');

    let r = form.elements["form:r"].value;
    dot.x = dot.x * (dot.r / r)
    dot.y = dot.y * (dot.r / r);
    let drawableX = (dot.x * height / 3 + r * rect.left + r * width / 2) / r;
    let drawableY = rect.bottom + rect.top - (dot.y * height / 3 + r * rect.top + r * height / 2) / r;

    ctx.fillStyle = dot.result == "true" ? "green" : "black";
    ctx.beginPath();
    ctx.arc(drawableX - rect.left, drawableY - rect.top, 5, 0, Math.PI*2, true);
    ctx.fill();
}

function makeDot(humanX, humanY, r) {
    let isHit = checkIfHit(humanX, humanY, r);
    return {x: humanX, y: humanY, r: r, result: isHit};
}



function makeLabelAlert(XorY) {
    if(XorY === "X") {
        $("#form\\:x_label")[0].innerText = "X must be between -4 and 4";
    } else {
        $("#form\\:y_label")[0].innerText = "Y must be between -3 and 5";
    }
}

function recoverAlertLabels() {
    $("#form\\:x_label")[0].innerText = "X";
    $("#form\\:y_label")[0].innerText = "Y";
}



function putADot(clientX, clientY) {
    let ctx = canvas.getContext('2d');

    let actualX = getHumanXFromPhysical(clientX);
    let actualY = getHumanYFromPhysical(clientY);
    ctx.fillStyle = checkIfHit(actualX, actualY, r);

    ctx.beginPath();
    ctx.arc(clientX - rect.left, clientY - rect.top, 5, 0, Math.PI*2, true);
    ctx.fill();
}

function checkIfHit(x, y, r) {

    let result = x <= 0 && x >= -r && y <= 0 && y >= -r ||
        x >= 0 && y <= 0 && x*x + y*y <= r*r/4 ||
        x <= 0 && y >= 0 && y <= r/2 + x/2 && x >= -r && y <= r/2
    return String(result);
}

function sendThisShit(humanX, humanY) {
    form.elements["form:x_hinput"].value = humanX.toFixed(2);
    form.elements["form:y_hinput"].value = humanY.toFixed(2);
    form.elements["form:submitButton"].click();
}

function changeR(selectedR) {
    form.elements["form:r"].value = selectedR.textContent;
    drawBase();
    drawDots();
}

