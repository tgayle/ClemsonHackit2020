import {Router} from 'express';
import { Suggestions } from '../entity/Suggestions';
import { Groups } from '../entity/Groups';
import { Users } from '../entity/Users';
import { Userlikesissues } from '../entity/Userlikesissues';
import { Userlikessuggestions } from '../entity/Userlikessuggestions';
import { getRepository } from 'typeorm';

const router = Router();

router.get('/', async (req, res) => {
  const data = await (Promise.all(
    (await Suggestions.find()).map(async (s) => {
      return {
        ...s,
        likes: (await getRepository(Userlikessuggestions).find({
          where: {
            issue: req.params.id
          }
        })).length
      }
    })
  ))
  res.json(data)
  
})

router.post('/', async (req, res) => {
  const newSuggestion = new Suggestions();
  newSuggestion.area = req.body.area;
  newSuggestion.date = req.body.date;
  newSuggestion.text = req.body.text;
  newSuggestion.user = req.body.user;
  newSuggestion.group = req.body.group;

  res.json(await newSuggestion.save())
})

router.post('/:id/like', async (req, res) => {
  // const user = Users.findOne({where: {userid: req.query.user}})
  // const suggestion = Suggestions.findOne(req.query.suggestionId)
  const liked = req.query.liked;

  const currentLike = await Userlikessuggestions.findOne({
    where: {
      user: req.query.user,
      suggestion: req.query.suggestionId
    }
  })

  switch (liked) {
    case 1:
      if (!currentLike) {
        await Userlikessuggestions.create({user: req.query.user, suggestion: Number(req.params.suggestionId)}).save()
      }
      break;
    case 2:
    case 0: 
      if (currentLike) {
        await currentLike.remove()
      }

      break;
  }

  res.send(200)
})

router.get('/:id', async (req, res) => {

  res.json({
    ...await Suggestions.findOne({where: {suggestionsid: req.params.id}}),
    likes: (await getRepository(Userlikessuggestions).find({
      where: {
        issue: req.params.id
      }
    })).length
  })
})

export default router;