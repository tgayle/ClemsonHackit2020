import "reflect-metadata";
import {createConnection} from "typeorm";
import express from 'express';
import morgan from 'morgan';

import { Issues } from "./entity/Issues";
import { Suggestions } from "./entity/Suggestions";

import userRoute from './routes/users';
import groupRoute from './routes/groups';
import suggestionRoute from './routes/suggestions';
import issueRoute from './routes/issues';
import { Groups } from "./entity/Groups";
import { Users } from "./entity/Users";
import { Userlikesissues } from "./entity/Userlikesissues";

async function main() {
    await createConnection()

    const app = express();
    app.use(morgan('dev'))

    app.use('/user', userRoute);
    app.use('/group', groupRoute);
    app.use('/suggestion', suggestionRoute);
    app.use('/issue', issueRoute);

    console.log(await Issues.find())
    // console.log(await Suggestions.find())
    // console.log(await Groups.find({relations: ['groupmembers']}));
    console.log(await Users.find({}))
    console.log(await Userlikesissues.find())

    app.listen(3000, () => console.log('bruh'));
}

main();
