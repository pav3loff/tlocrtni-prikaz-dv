import * as React from "react";
import { BaseControl } from "react-map-gl";

class Label extends BaseControl {
  _render() {
    const [geoDuzina, geoSirina] = this._context.viewport.project([
      this.props.geoDuzina,
      this.props.geoSirina,
    ]);

    const style = {
      position: "absolute",
      padding: "0px 4px 0px 4px",
      borderColor: "#000000",
      borderStyle: "solid",
      borderWidth: "1px",
      borderRadius: "4px",
      fontFamily: "Trebuchet MS",
      fontSize: "10px",
      fontWeight: "bold",
      top: geoSirina,
      left: geoDuzina,
      overflow: "hidden",
      ...this.props.style,
    };

    return (
      <div ref={this._containerRef} style={style}>
        {this.props.id}
      </div>
    );
  }
}

export default Label;
