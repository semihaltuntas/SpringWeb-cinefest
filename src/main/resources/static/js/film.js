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
    console.log(zoekIdInput.value)
    const response = await fetch(`films/${zoekIdInput.value}`, {method: "DELETE"});
    if (response.ok) {
        verbergFilmEnFouten();
        zoekIdInput.value = "";
        zoekIdInput.focus();
    } else {
        toon("storing");
    }
}
byId("bewaar").onclick = async function () {
    const nieuweTitelInput = byId("nieuweTitel");
    if (nieuweTitelInput.checkValidity()) {
        verberg("nieuweTitelFout")
        updateTitel(nieuweTitelInput.value);
    } else {
        toon("nieuweTitelFout")
        nieuweTitelInput.focus();
    }
}
byId("reserveer").onclick = async function () {
    verberg("emailAdresFout");
    verberg("plaatsenFout");
    const emailAdresInput = byId("emailAdres");
    if (!emailAdresInput.checkValidity()) {
        toon("emailAdresFout");
        emailAdresInput.focus();
        return;
    }
    const plaatsenInput = byId("plaatsen");
    if (!plaatsenInput.checkValidity()) {
        toon("plaatsenFout");
        plaatsenInput.focus();
        return;
    }
    const nieuweReservatie = {
        emailAdres: emailAdresInput.value,
        plaatsen: Number(plaatsenInput.value)
    };
    reserveer(nieuweReservatie);
};

async function reserveer(nieuweReservatie) {
    verberg("nietGevonden");
    verberg("storing");
    verberg("conflict");
    const response = await fetch(`films/${byId("zoekId").value}/reservaties`,
        {
            method: "POST",
            headers: {'Content-Type': "application/json"},
            body: JSON.stringify(nieuweReservatie)
        });
    if (response.ok) {
        window.location = "allefilms.html";
    } else {
        switch (response.status) {
            case 404:
                toon("nietGevonden");
                break;
            case 409:
                const responseBody = await response.json();
                setText("conflict", responseBody.message);
                toon("conflict");
                break;
            default:
                toon("storing");
        }
    }
}

async function updateTitel(nieuweTitel) {
    const response = await fetch(`films/${byId("zoekId").value}/titel`,
        {
            method: "PATCH",
            headers: {'Content-Type': "text/plain"},
            body: nieuweTitel
        });
    if (response.ok) {
        setText("titel", nieuweTitel)
    } else {
        toon("storing");
    }
}

function verbergFilmEnFouten() {
    verberg("film");
    verberg("zoekIdFout");
    verberg("nietGevonden");
    verberg("storing");
    verberg("conflict");
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