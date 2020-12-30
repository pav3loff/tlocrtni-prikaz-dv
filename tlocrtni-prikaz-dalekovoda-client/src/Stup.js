import React from "react";

import { Marker } from "react-map-gl";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircle } from "@fortawesome/free-solid-svg-icons";

import Izolator from "./Izolator";
import SpojnaTocka from "./SpojnaTocka";
import ElementInfo from "./ElementInfo";

import "./Stup.css";

function Stup(props) {
  return (
    <>
      <Marker latitude={props.geoSirina} longitude={props.geoDuzina}>
        <FontAwesomeIcon
          className={props.zoom > 21.5 ? "stup-detailed" : "stup"}
          icon={faCircle}
          onClick={() => {
            props.setSelectedElementId("STUP" + props.id);
          }}
        />
      </Marker>

      <ElementInfo
        elementId={"STUP" + props.id}
        displayItems={{
          Id: props.id,
          "Oblik glave stupa": props.oblikGlaveStupa,
          Proizvođač: props.proizvodac,
          "Vrsta zaštite": props.vrstaZastite,
          Visina: props.visina,
          "Oznaka uzemljenja": props.oznakaUzemljenja,
          Težina: props.tezina,
          Tip: props.tipStupa,
          Orijentacija: props.orijentacija,
          "Geo. širina": props.geoSirina,
          "Geo. dužina": props.geoDuzina,
        }}
        selectedElementId={props.selectedElementId}
        setSelectedElementId={props.setSelectedElementId}
      />

      {props.zoom > 21.5 && (
        <>
          {props.izolatori.map((izolator) => (
            <Izolator
              key={izolator.id}
              {...izolator}
              parentId={"STUP" + props.id + "IZL" + izolator.id}
              bounds={props.bounds}
              zoom={props.zoom}
              selectedElementId={props.selectedElementId}
              setSelectedElementId={props.setSelectedElementId}
            />
          ))}
          {props.spojneTockeZastitneUzadi.map((stzu) => (
            <SpojnaTocka
              key={stzu.id}
              elementId={"STUP" + props.id + "STZU" + stzu.id}
              id={stzu.id}
              tip="STZU"
              geoSirina={stzu.geoSirina}
              geoDuzina={stzu.geoDuzina}
              selectedElementId={props.selectedElementId}
              setSelectedElementId={props.setSelectedElementId}
            />
          ))}
        </>
      )}
    </>
  );
}

export default Stup;
