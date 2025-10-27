import React from "react";
import { Avatar, IconButton } from "@mui/material";
import { Rating, Box } from "@mui/material";
import type { Review } from "../../../types/reviewTypes";
import DeleteIcon from "@mui/icons-material/Delete";
import { red } from "@mui/material/colors";
import { useAppDispatch, useAppSelector } from "../../../Redux Toolkit/Store";
import { deleteReview } from "../../../Redux Toolkit/Customer/ReviewSlice";

import PersonIcon from "@mui/icons-material/Person";

interface ProductReviewCardProps {
  item: Review;
}

const ProductReviewCard = ({ item }: ProductReviewCardProps) => {
  const { user } = useAppSelector((store) => store);
  const dispatch = useAppDispatch();

  const handleDeleteReview = () => {
    dispatch(
      deleteReview({
        reviewId: item.id,
        jwt: localStorage.getItem("jwt") || "",
      })
    );
  };

  return (
    <div className="w-full bg-white rounded-xl shadow-md p-5 flex flex-col md:flex-row justify-between mb-5">
      {/* Left: Avatar + User Info */}
      <div className="flex gap-4">
        <Avatar
          sx={{ width: 56, height: 56, bgcolor: "#9155FD" }}
          alt={item?.user?.fullName || "User"}
        >
          {item?.user?.fullName ? (
            item.user.fullName.charAt(0).toUpperCase()
          ) : (
            <PersonIcon fontSize="small" />
          )}
        </Avatar>

        <div className="flex flex-col justify-center">
          <p className="font-semibold text-lg">{item?.user?.fullName}</p>
          <p className="text-sm text-gray-500">{item?.createdAt}</p>
          <Rating
            readOnly
            value={item?.rating || 0}
            name="half-rating"
            precision={0.5}
          />
        </div>
      </div>

      {/* Right: Review Content */}
      <div className="mt-4 md:mt-0 md:max-w-[70%]">
        <p className="text-gray-800 mb-3">{item?.reviewText}</p>

        {/* Product Images */}
        <div className="flex flex-wrap gap-2">
          {item?.productImages?.map((image, index) => (
            <img
              key={index}
              className="w-24 h-24 object-cover rounded-lg border"
              src={image}
              alt={`review-img-${index}`}
            />
          ))}
        </div>
      </div>

      {/* Delete Button */}
      {item?.user?.id === user.user?.id && (
        <div className="mt-3 md:mt-0">
          <IconButton onClick={handleDeleteReview}>
            <DeleteIcon sx={{ color: red[700] }} />
          </IconButton>
        </div>
      )}
    </div>
  );
};

export default ProductReviewCard;
