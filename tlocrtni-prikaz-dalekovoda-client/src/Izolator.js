import SpojnaTocka from "./SpojnaTocka";

function Izolator(props) {
  return (
    <>
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
