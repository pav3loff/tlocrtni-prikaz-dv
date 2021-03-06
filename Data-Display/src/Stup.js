import React from "react";

import { Marker } from "react-map-gl";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDiceD6, faCircle } from "@fortawesome/free-solid-svg-icons";

import Izolator from "./Izolator";
import SpojnaTocka from "./SpojnaTocka";
import ElementInfo from "./ElementInfo";
import imageBacva from "./images/imageBacva.svg";
import imageJela from "./images/imageJela.svg";
import imageDunav from "./images/imageDunav.svg";
import imagePortal from "./images/imagePortal.svg";
import imageMacka from "./images/imageMacka.svg";
import imageY from "./images/imageY.svg";
import imageDvostrukaJela from "./images/imageDvostrukaJela.svg";
import imageDvostrukiPortal from "./images/imageDvostrukiPortal.svg";
import imageDvostrukaMacka from "./images/imageDvostrukaMacka.svg";
import imageDvostrukiY from "./images/imageDvostrukiY.svg";
import { getStupStyle } from "./helperFunctions";
import Label from "./Label";
import { ZOOM_TRESHOLD } from "./constants";

import "./Stup.css";

function Stup(props) {
  return (
    <>
      <Marker latitude={props.geoSirina} longitude={props.geoDuzina}>
        <FontAwesomeIcon
          className={getStupStyle(props.oblikGlaveStupa, props.zoom)}
          icon={props.zoom < ZOOM_TRESHOLD ? faDiceD6 : faCircle}
          onClick={() => {
            props.setSelectedElementId("STUP" + props.id);
          }}
        />
      </Marker>

      <Label
        id={props.id}
        geoSirina={props.geoSirina}
        geoDuzina={props.geoDuzina}
        style={{
          background: "#ededed",
          marginLeft: props.zoom > ZOOM_TRESHOLD ? "20px" : "12px",
        }}
      />

      <ElementInfo
        elementId={"STUP" + props.id}
        displayImage={
          props.oblikGlaveStupa === "BACVA"
            ? imageBacva
            : props.oblikGlaveStupa === "JELA"
            ? imageJela
            : props.oblikGlaveStupa === "DUNAV"
            ? imageDunav
            : props.oblikGlaveStupa === "PORTAL"
            ? imagePortal
            : props.oblikGlaveStupa === "MACKA"
            ? imageMacka
            : props.oblikGlaveStupa === "Y"
            ? imageY
            : props.oblikGlaveStupa === "DVOSTRUKA_JELA"
            ? imageDvostrukaJela
            : props.oblikGlaveStupa === "DVOSTRUKI_PORTAL"
            ? imageDvostrukiPortal
            : props.oblikGlaveStupa === "DVOSTRUKA_MACKA"
            ? imageDvostrukaMacka
            : props.oblikGlaveStupa === "DVOSTRUKI_Y"
            ? imageDvostrukiY
            : ""
        }
        displayItems={{
          Id: props.id,
          "Oblik glave stupa": props.oblikGlaveStupa,
          Proizvođač: props.proizvodac,
          "Vrsta zaštite": props.vrstaZastite,
          Visina: props.visina,
          "Oznaka uzemljenja": props.oznakaUzemljenja,
          Težina: props.tezina,
          Tip: props.tipStupa,
        }}
        selectedElementId={props.selectedElementId}
        setSelectedElementId={props.setSelectedElementId}
      />

      {props.zoom > ZOOM_TRESHOLD && (
        <>
          {props.izolatori.map((izolator) => (
            <Izolator
              key={izolator.id}
              elementId={"STUP" + props.id + "IZOLATOR" + izolator.id}
              {...izolator}
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
