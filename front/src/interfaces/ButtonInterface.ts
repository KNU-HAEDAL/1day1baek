import { MouseEventHandler } from 'react';

export interface IButtonProps {
  onClick: MouseEventHandler<HTMLSpanElement>;
  color?: string;
  fontSize?: string;
  backgroundColor?: string;
  hoverColor?: string;
  hoverBackgroundColor?: string;
  width?: number | string;
  height?: number | string;
  pd?: number;
  blur?: boolean;
}

export interface IButtonWrapperProps {
  display?: 'flex' | 'block' | 'inline-block' | 'inline' | 'none';
  flexDirection?: 'row' | 'column' | 'row-reverse' | 'column-reverse';
  justifyContent?:
    | 'center'
    | 'flex-start'
    | 'flex-end'
    | 'space-between'
    | 'space-around';
  alignItems?: 'center' | 'flex-start' | 'flex-end' | 'stretch' | 'baseline';
}
