import { Marker } from "react-map-gl";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";

import SpojnaTocka from "./SpojnaTocka";
import ElementInfo from "./ElementInfo";
import imageIzolator from "./images/imageIzolator.svg";

import "./Izolator.css";
import { getIzolatorIconRotation } from "./helperFunctions";

function Izolator(props) {
  const geoSirinaIzolatora =
    (props.spojnaTockaIzolatora.geoSirina + props.spojnaTockaVodica.geoSirina) /
    2;
  const geoDuzinaIzolatora =
    (props.spojnaTockaIzolatora.geoDuzina + props.spojnaTockaVodica.geoDuzina) /
    2;

  return (
    <>
      {/* Izolator */}
      <>
        <Marker latitude={geoSirinaIzolatora} longitude={geoDuzinaIzolatora}>
          <FontAwesomeIcon
            className="izolator"
            icon={faBars}
            onClick={() => {
              props.setSelectedElementId(props.elementId);
            }}
            transform={
              "rotate-" +
              getIzolatorIconRotation(
                props.orijentacija,
                props.kutIzmedjuSpojneTockeVodicaIRavnineKonzole,
                props.spojnaTockaIzolatora.x,
                props.spojnaTockaVodica.z
              )
            }
          />
        </Marker>

        <ElementInfo
          elementId={props.elementId}
          displayImage={imageIzolator}
          displayItems={{
            Id: props.id,
            Materijal: props.materijal,
            Izvedba: props.izvedba,
            "Broj članaka": props.brojClanaka,
          }}
          selectedElementId={props.selectedElementId}
          setSelectedElementId={props.setSelectedElementId}
        />
      </>

      {/* Spojna točka izolatora */}
      <SpojnaTocka
        tip={"STI"}
        id={props.id}
        geoSirina={props.spojnaTockaIzolatora.geoSirina}
        geoDuzina={props.spojnaTockaIzolatora.geoDuzina}
        elementId={props.elementId + "STI" + props.id}
        selectedElementId={props.selectedElementId}
        setSelectedElementId={props.setSelectedElementId}
      />

      {/* Spojna točka vodiča */}
      <SpojnaTocka
        tip={"STV"}
        id={props.id}
        geoSirina={props.spojnaTockaVodica.geoSirina}
        geoDuzina={props.spojnaTockaVodica.geoDuzina}
        elementId={props.elementId + "STV" + props.id}
        selectedElementId={props.selectedElementId}
        setSelectedElementId={props.setSelectedElementId}
      />
    </>
  );
}

export default Izolator;
