const puppeteer = require('puppeteer');
const fs = require('fs');
const mime = require('mime');
const URL = require('url').URL;

(async() => {

const browser = await puppeteer.launch();
const page = await browser.newPage();

const responses = [];
var i = 'hola';
//page.on('requestfinished', resp => {
    //let head = resp.headers;
    /*if (resp.headers['content-type'].includes('application/pdf')){
        //const b = resp.buffer();
	//fs.writeFileSync(i+'.pdf', b);
        //i = i + 1;
       console.log(resp);
       console.log('olololololololololololololololo\n\n\n\n');
    }*/
    /*if (head.includes('.pdf')){
        const b = resp.buffer();
        fs.writeFileSync('hola.pdf', b);
    }*/
    //console.log(resp.headers['content-type'].includes('application/pdf'));
    //const b = resp.buffer();
    //fs.writeFileSync(i+'.pdf', b);
    //i = i + 1;
  //  console.log(resp);
//});


await page.goto('https://www.libretamilitar.mil.co/Modules/Consult/MilitaryCardCertificate', {waitUntil: 'networkidle'});
    await page.press('ArrowDown');
    await page.press('Tab' );
    await page.type('1020761735');
    await page.click('#ctl00_MainContent_btnGenerate');
    await page.waitForNavigation({waitUntil: 'networkidle'});
    //await page.pdf({path: 'screenshot.pdf', format: 'A4'});
    await page.click('#ctl00_MainContent_imgBtnSeeCertificate');
    await page.waitForNavigation({waitUntil: 'networkidle'});
    //const buffer = await resp.buffer();
    //console.log('response buffer', buffer);
    await browser.close();
})();

