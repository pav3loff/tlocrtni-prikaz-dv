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
