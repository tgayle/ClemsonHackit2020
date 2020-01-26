import { Column, Entity, OneToMany, PrimaryGeneratedColumn, BaseEntity } from "typeorm";
import { Groupmembers } from "./Groupmembers";
import { Issues } from "./Issues";
import { Suggestions } from "./Suggestions";

@Entity("GROUPS", { schema: "innodb" })
export class Groups extends BaseEntity {
  @Column("varchar", { name: "DATECREATED", nullable: true, length: 45 })
  datecreated: string | null;

  @Column("varchar", { name: "AREA", nullable: true, length: 45 })
  area: string | null;

  @Column("varchar", { name: "DESCRIPTION", nullable: true, length: 500 })
  description: string | null;

  @Column("int", { name: "MEMBERS", nullable: true })
  members: number | null;

  @PrimaryGeneratedColumn({ type: "int", name: "GROUPID" })
  groupid: number;

  @OneToMany(
    () => Groupmembers,
    groupmembers => groupmembers.group
  )
  groupmembers: Groupmembers[];

  @OneToMany(
    () => Issues,
    issues => issues.group
  )
  issues: Issues[];

  @OneToMany(
    () => Suggestions,
    suggestions => suggestions.group2
  )
  suggestions: Suggestions[];
}
