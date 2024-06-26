"use strict";
import {byId, toon, verberg} from "./util.js";

const response = await fetch("films");
if (response.ok) {
    const films = await response.json();
    const filmsBody = byId("filmsBody");
    console.log(filmsBody)
    for (const film of films) {
        const tr = filmsBody.insertRow();
        tr.insertCell().innerText = film.id;
        tr.insertCell().innerText = film.titel;
        tr.insertCell().innerText = film.jaar;
        tr.insertCell().innerText = film.vrijePlaatsen;
    }
} else {
    toon("storing");
}