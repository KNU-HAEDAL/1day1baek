import { z } from 'zod';

export const CreateGroupSchema = z.object({
  name: z.string().min(1, { message: '이름은 필수로 입력해주셔야 합니다.' }),
  isPrivate: z.boolean(),
  password: z.string(),
  goalSolveCount: z.number({
    invalid_type_error: '숫자만 입력가능합니다.',
  }),
  fine: z.number({
    invalid_type_error: '숫자만 입력가능합니다.',
  }),
});

export type CreateGroupSchemaType = z.infer<typeof CreateGroupSchema>;
