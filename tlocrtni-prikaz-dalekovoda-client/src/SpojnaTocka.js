import { faCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Marker } from "react-map-gl";

import ElementInfo from "./ElementInfo";

import "./SpojnaTocka.css";

function SpojnaTocka(props) {
  return (
    <>
      <Marker latitude={props.geoSirina} longitude={props.geoDuzina}>
        <FontAwesomeIcon
          className={
            props.tip === "STI"
              ? "spojna-tocka-izolatora"
              : props.tip === "STV"
              ? "spojna-tocka-vodica"
              : "spojna-tocka-zastitnog-uzeta"
          }
          icon={faCircle}
          onClick={() => {
            props.setSelectedElementId(props.elementId);
          }}
        />
      </Marker>

      <ElementInfo
        elementId={props.elementId}
        displayItems={{
          "Spojna točka": props.tip,
          "Geo. širina": props.geoSirina,
          "Geo. dužina": props.geoDuzina,
        }}
        selectedElementId={props.selectedElementId}
        setSelectedElementId={props.setSelectedElementId}
      />
    </>
  );
}

export default SpojnaTocka;
