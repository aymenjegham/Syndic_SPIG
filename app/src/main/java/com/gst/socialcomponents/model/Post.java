/*
 * Copyright 2017 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gst.socialcomponents.model;

import android.util.Log;

import com.gst.socialcomponents.enums.ItemType;
import com.gst.socialcomponents.utils.FormatterUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Post implements Serializable, LazyLoading {

    private String id;
    private String title;
    private String description;
    private long createdDate;
    private String imagePath;
    private String imageTitle;
    private String authorId;
    private long commentsCount;
    private long likesCount;
    private long watchersCount;
    private boolean hasComplain;
    private ItemType itemType;
     private String residence;
     private String moderator;
     private boolean isvideo;
     private long publier;
     private  String compteur;
     private String contrat;
    private long datefacture;
    private long montant;



    public Post(String id, String title, String description, long createdDate, String imagePath, String imageTitle, String authorId, long commentsCount,
                long likesCount, long watchersCount, boolean hasComplain, ItemType itemType, String residence, String moderator, boolean isvideo,long publier,String compteur,String contrat,long datefacture,
                long montant) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.imagePath = imagePath;
        this.imageTitle = imageTitle;
        this.authorId = authorId;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
        this.watchersCount = watchersCount;
        this.hasComplain = hasComplain;
        this.itemType = itemType;
        this.residence = residence;
        this.moderator = moderator;
        this.isvideo = isvideo;
        this.publier=publier;
        this.compteur=compteur;
        this.contrat=contrat;
        this.datefacture=datefacture;
        this.montant=montant;
    }

    public boolean isIsvideo() {
        return isvideo;
    }

    public void setIsvideo(boolean isvideo) {
        this.isvideo = isvideo;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public Post() {
        this.createdDate = new Date().getTime();
        itemType = ItemType.ITEM;
    }

    public Post(ItemType itemType) {
        this.itemType = itemType;
        setId(itemType.toString());
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(long watchersCount) {
        this.watchersCount = watchersCount;
    }

    public boolean isHasComplain() {
        return hasComplain;
    }

    public void setHasComplain(boolean hasComplain) {
        this.hasComplain = hasComplain;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("title", title);
        result.put("description", description);
        result.put("createdDate", createdDate);
        result.put("imagePath", imagePath);
        result.put("imageTitle", imageTitle);
        result.put("authorId", authorId);
        result.put("commentsCount", commentsCount);
        result.put("likesCount", likesCount);
        result.put("watchersCount", watchersCount);
        result.put("hasComplain", hasComplain);
        result.put("createdDateText", FormatterUtil.getFirebaseDateFormat().format(new Date(createdDate)));
        result.put("moderator",moderator);
        result.put("residence",residence);
        result.put("isvideo",isvideo);
        result.put("publier", publier);
        result.put("compteur",compteur);
        result.put("contrat",contrat);
        result.put("datefacture", datefacture);
        result.put("montant",montant);



        return result;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public void setItemType(ItemType itemType) {

    }


    public long getPublier() {
        return publier;
    }

    public void setPublier(long publier) {
        this.publier = publier;
    }


    public String getCompteur() {
        return compteur;
    }

    public void setCompteur(String compteur) {
        this.compteur = compteur;
    }

    public String getContrat() {
        return contrat;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }

    public long getDatefacture() {
        return datefacture;
    }

    public void setDatefacture(long datefacture) {
        this.datefacture = datefacture;
    }

    public long getMontant() {
        return montant;
    }

    public void setMontant(long montant) {
        this.montant = montant;
    }
}
