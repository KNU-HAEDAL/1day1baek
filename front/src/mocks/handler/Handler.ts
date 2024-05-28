import { SearchHandler } from '@mocks/handler/search/SearchHandler';
import { UserHandler } from '@mocks/handler/user/UserHandler';

export const Handlers = [...UserHandler, ...SearchHandler];
