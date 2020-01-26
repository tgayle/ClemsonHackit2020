import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn,
  BaseEntity
} from "typeorm";
import { Groups } from "./Groups";
import { Users } from "./Users";
import { Suggestions } from "./Suggestions";
import { Userlikesissues } from "./Userlikesissues";

@Index("USERID_idx", ["user"], {})
@Index("GROUPID_idx", ["groupid"], {})
@Entity("ISSUES", { schema: "innodb" })
export class Issues extends BaseEntity {
  @PrimaryGeneratedColumn({ type: "int", name: "ISSUEID" })
  issueid: number;

  @Column("varchar", { name: "DATE", nullable: true, length: 45 })
  date: string | null;

  @Column("int", { name: "USER", nullable: true })
  user: number | null;

  @Column("varchar", { name: "AREA", nullable: true, length: 45 })
  area: string | null;

  @Column("int", { name: "GROUPID", nullable: true })
  groupid: number | null;

  @Column("int", { name: "LIKES", nullable: true })
  likes: number | null;

  @Column("varchar", { name: "TITLE", nullable: true, length: 45 })
  title: string | null;

  @Column("tinyint", { name: "RESOLVED", nullable: true })
  resolved: number | null;

  @Column("varchar", { name: "DESCRIPTION", nullable: true, length: 500 })
  description: string | null;

  @ManyToOne(
    () => Groups,
    groups => groups.issues,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "GROUPID", referencedColumnName: "groupid" }])
  group: Groups;

  @ManyToOne(
    () => Users,
    users => users.issues,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "USER", referencedColumnName: "userid" }])
  user2: Users;

  @OneToMany(
    () => Suggestions,
    suggestions => suggestions.issue2
  )
  suggestions: Suggestions[];

  @OneToMany(
    () => Userlikesissues,
    userlikesissues => userlikesissues.issue2
  )
  userlikesissues: Userlikesissues[];
}
