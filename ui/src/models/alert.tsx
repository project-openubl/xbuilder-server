export type Variant = "success" | "danger" | "info" | "warning";
export interface AlertModel {
  title: string;
  variant: Variant;
  description?: any;
  dismissable?: boolean; // if is false, notification will disappear automatically after 5s, otherwise will show close button,
  dismissDelay?: number;
}
