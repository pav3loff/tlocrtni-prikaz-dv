export function isVisible(elementLatitude, elementLongitude, bounds) {
  if (
    elementLatitude > bounds.sw.lat &&
    elementLongitude > bounds.sw.lng &&
    elementLatitude < bounds.ne.lat &&
    elementLongitude < bounds.ne.lng
  ) {
    return true;
  }

  return false;
}

export function getStupStyle(tipStupa, zoom) {
  if (tipStupa === "JELA") {
    return zoom < 21.5 ? "stup-jela" : "stup-jela-detailed";
  } else if (tipStupa === "BACVA") {
    return zoom < 21.5 ? "stup-bacva" : "stup-bacva-detailed";
  } else if (tipStupa === "DUNAV") {
    return zoom < 21.5 ? "stup-dunav" : "stup-dunav-detailed";
  } else if (tipStupa === "PORTAL") {
    return zoom < 21.5 ? "stup-portal" : "stup-portal-detailed";
  } else if (tipStupa === "Y") {
    return zoom < 21.5 ? "stup-y" : "stup-y-detailed";
  } else if (tipStupa === "MACKA") {
    return zoom < 21.5 ? "stup-macka" : "stup-macka-detailed";
  } else if (tipStupa === "DVOSTRUKI_PORTAL") {
    return zoom < 21.5
      ? "stup-dvostruki-portal"
      : "stup-dvostruki-portal-detailed";
  } else if (tipStupa === "DVOSTRUKA_JELA") {
    return zoom < 21.5 ? "stup-dvostruka-jela" : "stup-dvostruka-jela-detailed";
  } else if (tipStupa === "DVOSTRUKA_MACKA") {
    return zoom < 21.5
      ? "stup-dvostruka-macka"
      : "stup-dvostruka-macka-detailed";
  } else if (tipStupa === "DVOSTRUKI_Y") {
    return zoom < 21.5 ? "stup-dvostruki-y" : "stup-dvostruki-y-detailed";
  }
}
