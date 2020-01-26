import {Router} from 'express';
import { Groups } from '../entity/Groups';

const router = Router();

router.get('/', async (req, res) => {
  res.json(await Groups.find({relations: ['groupmembers']}))
})

router.get('/:id', async (req, res) => {
  res.json(await Groups.findOne({where: {groupid: req.params.id}, relations: ['groupmembers']}))
})

export default router;