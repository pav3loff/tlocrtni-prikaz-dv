import { faCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import { Marker, Popup } from "react-map-gl";

import ElementInfo from "./ElementInfo";

import "./SpojnaTocka.css";

const popupTipSize = 5;
const popupOffsetLeft = 4;
const popupOffsetTop = 2;

function SpojnaTocka(props) {
  const [isPopupVisible, setPopupVisible] = useState(false);

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
          onMouseEnter={() => setPopupVisible(true)}
          onMouseLeave={() => setPopupVisible(false)}
        />
      </Marker>

      {isPopupVisible && (
        <Popup
          className="popup"
          latitude={props.geoSirina}
          longitude={props.geoDuzina}
          closeButton={false}
          closeOnClick={false}
          sortByDepth={true}
          tipSize={popupTipSize}
          offsetLeft={popupOffsetLeft}
          offsetTop={popupOffsetTop}
        >
          <p className="popup-text">
            {props.tip === "STI"
              ? "Spojna točka izolatora"
              : props.tip === "STV"
              ? "Spojna točka vodiča"
              : "Spojna točka zaštitnog užeta"}
          </p>
        </Popup>
      )}

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
