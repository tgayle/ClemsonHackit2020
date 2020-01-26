import {Router} from 'express';
import { Issues } from '../entity/Issues';
import { Suggestions } from '../entity/Suggestions';
import { Groups } from '../entity/Groups';
import { Userlikesissues } from '../entity/Userlikesissues';
import { Userlikessuggestions } from '../entity/Userlikessuggestions';
import { Users } from '../entity/Users';
import { getRepository } from 'typeorm';

const router = Router();

router.get('/', async (req, res) => {
  console.log(req.query)
  const issues = await Promise.all((await Issues.find()).map(async (issue) => {
    console.log(issue.issueid)
    return {
      ...issue,
      likes: (await getRepository(Userlikesissues).find({
        where: {
          issue: issue.issueid
        }
      })).length
    };
  }))
  res.json(issues)
})

router.get('/:id', async (req, res) => {
  res.json({
    ...await Issues.findOne({where: {issueId: Number(req.params.id)}}),
    likes: (await getRepository(Userlikesissues).find({
      where: {
        issue: req.params.issueid
      }
    })).length
  })
})

router.get('/:id/like', async (req, res) => {
  const currentLike = await Userlikesissues.findOne({
    where: {
      user: req.query.user,
      issue: req.query.issue
    }
  })

  res.json(!!currentLike)
})


router.post('/:id/like', async (req, res) => {
  const liked = req.query.liked;

  const currentLike = await Userlikesissues.findOne({
    where: {
      user: req.query.user,
      suggestion: req.query.suggestionId
    }
  })

  console.log(req.query)
  console.log(req.params)

  if (liked == '1') {
    if (!currentLike) {
      const like = new Userlikesissues();
      like.user2 = await Users.findOne({
        where: {
          userid: Number(req.query.user)
        }
      })
      like.issue2 = await Issues.findOne({
        where: {
          issueid: Number(req.params.id)
        }
      })
      await like.save();
    }
  } else {
    if (currentLike) {
      await currentLike.remove()
    }
  }

  res.sendStatus(200)
})

router.get('/:id/suggestions', async (req, res) => {
  res.json(await Suggestions.find({where: {issueId: req.params.id}}))
})

export default router;