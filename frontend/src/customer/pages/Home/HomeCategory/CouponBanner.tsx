import React, { useEffect, useState } from "react";
import { api } from "../../../../config/Api";


interface Coupon {
  code: string;
  discountPercentage: number;
  validityEndDate: string;
}

const CouponBanner = () => {
  const [coupons, setCoupons] = useState<Coupon[]>([]);

  useEffect(() => {
    const fetchCoupons = async () => {
      try {
        const response = await api.get("/coupons/active");
        setCoupons(response.data);
      } catch (err) {
        console.error("Failed to fetch coupons", err);
      }
    };
    fetchCoupons();
  }, []);

  if (coupons.length === 0) return null;

  return (
    <div className="bg-green-100 text-green-900 p-3 rounded-md mb-4">
      🎁 New Coupons Available! Use code{" "}
      {coupons.map(c => c.code).join(", ")} to get discounts.
    </div>
  );
};

export default CouponBanner;
