"use strict";
import {byId, toon, verberg, verwijderChildElementenVan} from "./util.js";
byId("zoek").onclick = async function () {
    verbergMedewerkersEnFouten();
    const stukVoornaamInput = byId("stukVoornaam");
    const stukFamilienaamInput = byId("stukFamilienaam");
    findByStukVoornaamEnStukFamilienam(stukVoornaamInput.value,
        stukFamilienaamInput.value);
};
function verbergMedewerkersEnFouten() {
    verberg("medewerkersTable");
    verberg("storing");
}
async function findByStukVoornaamEnStukFamilienam(stukVoornaam, stukFamilienaam) {
    const response = await fetch(
        `medewerkers?stukVoornaam=${stukVoornaam}&stukFamilienaam=${stukFamilienaam}`);
    if (response.ok) {
        const medewerkers = await response.json();
        toon("medewerkersTable");
        const medewerkersBody = byId("medewerkersBody");
        verwijderChildElementenVan(medewerkersBody);
        for (const medewerker of medewerkers) {
            const tr = medewerkersBody.insertRow();
            tr.insertCell().innerText = medewerker.id;
            tr.insertCell().innerText = medewerker.voornaam;
            tr.insertCell().innerText = medewerker.familienaam;
        }
    } else {
        toon("storing");
    }
}