function drawBase() {
    let canvas = document.getElementById('graphicCanvas');
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
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('graphicCanvas').addEventListener('click', (e) => {
        processClickInput(e);
    })
    drawBase();
})

function processClickInput(e) {
        drawBase();
        putADot(e.clientX, e.clientY);
        sendThisShit(e)
}


function putADot(x, y) {
    const canvas = $("#graphicCanvas")[0];
    const rect = canvas.getBoundingClientRect();
    let ctx = canvas.getContext('2d');
    ctx.beginPath();
    ctx.moveTo(x - rect.left, y - rect.top);
    ctx.arc(x - rect.left, y - rect.top, 5, 0, Math.PI*2, true);
    ctx.fill();
}

function sendThisShit(e) {
    const form = $("#form")[0];
    const rect = $("#graphicCanvas")[0].getBoundingClientRect();
    let r = form.elements["form:r"].value;
    const width = rect.right - rect.left;
    const height = rect.bottom - rect.top;
    let x = r * (e.clientX - rect.left - width / 2) / (height / 3);
    let y = -r * (e.clientY - rect.top - height / 2) / (height / 3);
    form.elements["form:x_hinput"].value = x.toFixed(2);
    form.elements["form:y_hinput"].value = y.toFixed(2);
    form.elements["form:submitButton"].click();
}

function changeR(selectedR) {
    $("#form")[0].elements["form:r"].value = selectedR.textContent;
}

