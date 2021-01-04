import { Drawer, List, ListItem } from "@material-ui/core";

import "./ElementInfo.css";

function ElementInfo(props) {
  return (
    <Drawer
      anchor="left"
      open={props.elementId === props.selectedElementId}
      onClose={() => props.setSelectedElementId(undefined)}
    >
      <List>
        {props.displayImage && (
          <ListItem className="list-item-image">
            <img alt={props.elementId} src={props.displayImage} />
          </ListItem>
        )}
        {Object.keys(props.displayItems).map((key) => {
          return (
            <ListItem key={props.elementId + key + props.displayItems[key]}>
              <p>
                <strong>{key}: </strong> {props.displayItems[key]}
              </p>
            </ListItem>
          );
        })}
      </List>
    </Drawer>
  );
}

export default ElementInfo;
