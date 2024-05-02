"use strict";
import {byId, toon, verberg, setText} from "./util.js";

byId("zoek").onclick = async function () {
    verbergFilmEnFouten();
    const zoekIdInput = byId("zoekId");
    console.log(zoekIdInput)
    if (zoekIdInput.checkValidity()) {
        findById(zoekIdInput.value);
    } else {
        toon("zoekIdFout");
        zoekIdInput.focus();
    }
}
byId("verwijder").onclick = async function () {
    const zoekIdInput = byId("zoekId");
    const response = await fetch(`films/${zoekIdInput.value}`, {method: "DELETE"});
    if (response.ok) {
        verbergFilmEnFouten();
        zoekIdInput.value = "";
        zoekIdInput.focus();
    } else {
        toon("storing");
    }
}

function verbergFilmEnFouten() {
    verberg("film");
    verberg("zoekIdFout");
    verberg("nietGevonden");
    verberg("storing");
}

async function findById(id) {
    const response = await fetch(`films/${id}`);
    if (response.ok) {
        const film = await response.json();
        toon("film");
        setText("titel", film.titel);
        setText("jaar", film.jaar)
        setText("vrijeplaatsen", film.vrijePlaatsen)
    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}