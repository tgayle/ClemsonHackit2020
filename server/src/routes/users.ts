import {Router} from 'express';
import { Users } from '../entity/Users';
import { Groups } from '../entity/Groups';

const router = Router();

router.get('/', async (req, res) => {
  res.json(await Users.find())
})

router.get('/:id', async (req, res) => {
  res.json(await Users.findOne({where: {userid: req.params.id}}))
})

export default router;