"use strict";
import {setText, toon} from "./util.js";

const response = await fetch("films/totaalvrijeplaatsen");
console.log(response)
if (response.ok) {
    const body = await response.text();
    setText("totaal", body);
} else {
    toon("storing");
}
