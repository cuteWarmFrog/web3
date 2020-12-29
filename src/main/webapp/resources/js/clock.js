function showDateTime() {
    let date = new Date();
    let oneOneByOne = String(date).split(" ").slice(0, 5);
    oneOneByOne.push(date.getMilliseconds());
    if(oneOneByOne[5] < 100)
        oneOneByOne[5] = "0" + oneOneByOne[5];

    document.getElementById("clock").innerText = oneOneByOne.join(" ");
}

setInterval(showDateTime, 25);
