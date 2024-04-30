"use strict";
import {byId, toon, verberg, verwijderChildElementenVan} from "./util.js";
byId("zoek").onclick = async function () {
    verbergPizzasEnFouten();
    const jaarInput = byId("jaar");
    if (jaarInput.checkValidity()) {
        findByJaar(jaarInput.value);
    } else {
        toon("jaarFout");
        jaarInput.focus();
    }
};
function verbergPizzasEnFouten() {
    verberg("filmsTable");
    verberg("jaarFout");
    verberg("storing");
}
async function findByJaar(jaar) {
    const response = await fetch(`films?jaar=${jaar}`);
    if (response.ok) {
        const films = await response.json();
        console.log(films)
        toon("filmsTable");
        const filmsBody = byId("filmsBody");
        console.log(filmsBody)
        verwijderChildElementenVan(filmsBody);
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
}