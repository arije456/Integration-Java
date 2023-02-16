/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author zeine
 */
public class LikeBlog {
     private int idLike;
    private int iduser;
    private int idBlog;
    private int likeEtat;

    public LikeBlog() {
    }

    public LikeBlog(int idLike, int iduser, int idBlog,int likeEtat) {
        this.idLike = idLike;
        this.iduser = iduser;
        this.idBlog = idBlog;
        this.likeEtat=likeEtat;    }

    public LikeBlog(int iduser, int idBlog,int likeEtat) {
        this.iduser = iduser;
        this.idBlog = idBlog;
        this.likeEtat =likeEtat;
    }
    
    
}
