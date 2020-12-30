import { Drawer, List, ListItem } from "@material-ui/core";

function ElementInfo(props) {
  return (
    <Drawer
      anchor="left"
      open={props.elementId === props.selectedElementId}
      onClose={() => props.setSelectedElementId(undefined)}
    >
      <List>
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
