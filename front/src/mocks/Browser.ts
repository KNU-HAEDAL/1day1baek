import { Handlers } from '@mocks/handler/Handler';
import { setupWorker } from 'msw/browser';

export const worker = setupWorker(...Handlers);
