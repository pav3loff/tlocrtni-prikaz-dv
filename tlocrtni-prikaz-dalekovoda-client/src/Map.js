import React from "react";
import ReactMapGL from "react-map-gl";

import { TOKEN } from "./mapboxToken";
import { STUPOVI } from "./dummyData";
import Stup from "./Stup";
import { isVisible } from "./helperFunctions";

class Map extends React.Component {
  constructor(props) {
    super(props);

    this.updateViewport = this.updateViewport.bind(this);
    this.setSelectedElementId = this.setSelectedElementId.bind(this);

    this.state = {
      viewport: {
        width: "100vw",
        height: "100vh",
        latitude: 45.867294,
        longitude: 15.920233,
        zoom: 10,
      },
      stupovi: [],
      bounds: {},
      selectedElementId: undefined,
      touched: false,
    };
  }

  componentDidMount() {
    const stupovi = STUPOVI;
    const bounds = this.mapRef.getMap().getBounds();

    this.setState({
      stupovi: stupovi,
      bounds: { sw: bounds._sw, ne: bounds._ne },
    });
  }

  setSelectedElementId(id) {
    this.setState({ selectedElementId: id });
  }

  updateViewport(viewport) {
    if (!this.state.touched) {
      this.setState({ viewport: viewport, touched: true });
    } else {
      const bounds = this.mapRef.getMap().getBounds();

      this.setState({
        viewport: viewport,
        bounds: { sw: bounds._sw, ne: bounds._ne },
      });
    }
  }

  render() {
    return (
      <ReactMapGL
        ref={(mapRef) => (this.mapRef = mapRef)}
        {...this.state.viewport}
        onViewportChange={(viewport) => this.updateViewport(viewport)}
        mapboxApiAccessToken={TOKEN}
        mapStyle="mapbox://styles/mapbox/streets-v11"
      >
        {this.state.stupovi.map((stup) => {
          return isVisible(
            stup.geoSirina,
            stup.geoDuzina,
            this.state.bounds
          ) ? (
            <Stup
              key={stup.id}
              {...stup}
              bounds={this.state.bounds}
              zoom={this.state.viewport.zoom}
              selectedElementId={this.state.selectedElementId}
              setSelectedElementId={this.setSelectedElementId}
            />
          ) : undefined;
        })}
      </ReactMapGL>
    );
  }
}

export default Map;
